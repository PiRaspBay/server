(ns piraspbay.handlers.api
  (:use [piraspbay.rasp :only [ping]]
        [piraspbay.db :as db]))

(defn register [req]
  (defn now [] (.getTime (java.util.Date.)))
  (let [key (-> req :params :key)
       name (-> req :params :name)
       address (-> req :remote-addr)]
       (ping name key address now)))

(defn profile [req]
  (let [me (-> req :params :me)
       user (-> req :params :user)]
       [me user]))

(defn friend [req]
  (let [me (-> req :params :me)]
       (to-public (db/find-friends me))))

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
