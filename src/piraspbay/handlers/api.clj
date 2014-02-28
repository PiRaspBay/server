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

(defn auth [handler]
  (fn [req] (handler req (-> req :params :me))))

(def profile (auth (fn [req me]
                     (let [name (-> req :params :user)
                           user (db/find-user name)]
                       (user-json user)))))

(def get-config (auth (fn [req me] "config!")))

(def friend (auth (fn [req me]
                    (map user-json (db/find-friends me)))))

(def request (auth (fn [req me] (db/find-requests me))))


(def accept (auth (fn [req me]
                    (let [user (-> req :params :user)]
                      (if (db/accept-request me user) "ok" "ko")))))

(def decline (auth (fn [req me]
  (let [user (-> req :params :user)]
    (if (db/remove-request me user) "ok" "ko")))))

(def new-request (auth (fn [req me]
                         (let [user (-> req :params :user)]
                           (db/add-request me user)))))
