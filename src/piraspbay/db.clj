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

; USER

(defn find-user [name]
  (no-id (mc/find-one-as-map user-coll {:name name})))

(defn find-users [names]
  (map no-id (mc/find-maps user-coll {:name {:$in names}})))

; FRIEND

(defn find-friends [name]
  (let [objs (mc/find-maps relation-coll {:users name})
        names (distinct (flatten (map :users objs)))
        friends (vec (remove #{name} names))]
    (find-users friends)))

(defn add-friend [u1, u2]
  (mc/insert relation-coll {:users [u1 u2]}))

; REQUEST

(defn find-requests [name]
  (map no-id (mc/find-maps request-coll {:to name})))

(defn find-request [me user]
  (mc/find-one-as-map request-coll {:from me :to user}))

(defn remove-request [to from]
  (mc/remove request-coll {:from from :to to}))

(defn accept-request [me user]
  (if (find-request me user)
    (do
      (remove-request me user)
      (add-friend me user)
      true)
    false))
