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

(defn is-friend? [me user]
  (let [friend (mc/find-one-as-map relation-coll {:users {:$all [me user]}})]
    (if friend true false)))

(defn find-friends [name]
  (let [objs (mc/find-maps relation-coll {:users name})
        names (distinct (flatten (map :users objs)))
        friends (vec (remove #{name} names))]
    (find-users friends)))

(defn add-friend [u1, u2]
  (mc/insert relation-coll {:users [u1 u2]}))

; REQUEST

(defn find-requests [to]
  (map no-id (mc/find-maps request-coll {:to to})))

(defn find-request [from to]
  (mc/find-one-as-map request-coll {:from from :to to}))

(defn remove-request [from to]
  (mc/remove request-coll {:from from :to to}))

(defn accept-request [from to]
  (if (find-request from to)
    (do
      (remove-request from to)
      (add-friend from to)
      true)
    false))

(defn add-request [from to]
  (let [request (find-request from to)]
    (if (is-friend? from to)
      false
      (if request
        (no-id request)
        (no-id (mc/insert-and-return request-coll {:from from :to to}))))))
