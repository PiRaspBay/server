(ns piraspbay.handlers.api
  (:use [piraspbay.rasp :only [ping]]
        [piraspbay.db :as db])
  (:require [clj-time.core :as time]
            [clj-time.coerce :as coerce]))

(defn online? [lastSeen]
  (time/before? (coerce/from-long lastSeen) (time/minus (time/now) (time/minutes 10))))

(defn user-json [user] {:name (:name user)
                        :online (online? (:lastSeen user))})

(defn register [req]
  (let [key (-> req :params :key)
        name (-> req :params :name)
        address (-> req :remote-addr)]
    (ping name key address (time/now))))

(defn profile [req]
  (let [me (-> req :params :me)
        name (-> req :params :user)
        user (db/find-user name)]
    (user-json user)))

(defn friend [req]
  (let [me (-> req :params :me)]
    (map user-json (db/find-friends me))))

(defn request [req]
  (let [me (-> req :params :me)]
    [me]))

(defn accept [req]
  (let [me (-> req :params :me)
        user (-> req :params :user)]
    [me user]))

(defn delete [req]
  (let [me (-> req :params :me)
        user (-> req :params :user)]
    [me user]))

(defn new-request [req]
  (let [me (-> req :params :me)
        user (-> req :params :user)]
    [me user]))
