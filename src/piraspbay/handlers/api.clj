(ns piraspbay.handlers.api
  (:use [piraspbay.rasp :only [ping]]
        [piraspbay.db :as db])
  (:require [piraspbay.tmpls :as tmpl]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]))

(defn online? [lastSeen]
  (time/before? (coerce/from-long lastSeen) (time/minus (time/now) (time/minutes 10))))

(defn user-json [user] {:name (:name user)
                        :online (online? (:lastSeen user))})

(defn err [status body] {:status status
                         :headers {"Content-Type" "text/plain; charset=utf-8"}
                         :body body})

(defn not-found [what] (err 404 (str what " not found!")))

(defn register [req]
  (let [key (-> req :params :key)
        name (-> req :params :name)
        address (-> req :remote-addr)]
    (ping name key address (time/now))))

(defn auth [handler]
  (fn [req] (if-let [me (db/find-user (-> req :params :me))]
              (handler req (:name me))
              (err 401 "Authentication required!"))))

(def profile (auth (fn [req me]
                     (if-let [user (db/find-user (-> req :params :user))]
                       (user-json user)
                       (not-found "user")))))

(def configure
  (auth (fn [req me]
          (let [friends (db/find-friends me)]
            (tmpl/configure {:friends friends})))))

(def friend (auth (fn [req me]
                    (map user-json (db/find-friends me)))))

(def request (auth (fn [req me] (db/find-requests me))))

(def accept (auth (fn [req me]
                    (if-let [user (-> req :params :user)]
                      (if (db/accept-request user me)
                        "ok"
                        (not-found "request"))
                      (not-found "user")))))

(def decline (auth (fn [req me]
                     (if-let [user (-> req :params :user)]
                       (if (db/remove-request user me)
                         "ok"
                         (not-found "request"))
                       (not-found "user")))))

(def new-request (auth (fn [req me]
                         (if-let [user (-> req :params :user)]
                           (if (db/add-request me user)
                             "ok"
                             (err 400 "Can't send this request"))
                           (not-found "user")))))
