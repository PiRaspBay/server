(ns piraspbay.db
  (:use [piraspbay.config :only [cfg]])
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(def config (cfg :mongodb))
(def user-coll (:user-coll config))
(def request-coll (:request-coll config))
(def relation-coll (:relation-coll config))

; remove mongodb _id from the object
(defn no-id [obj] (dissoc obj :_id))

(defn find-user [name]
  (no-id (mc/find-one user-coll {:name name})))

(defn find-friends [name]
  (map no-id (mc/find-maps relation-coll {:users name})))
