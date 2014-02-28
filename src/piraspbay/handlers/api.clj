(ns piraspbay.handlers.api
  (:use [piraspbay.rasp :only [ping]]))

(defn register [req]
  (defn now [] (.getTime (java.util.Date.)))
  (let [key (-> req :params :key)
       name (-> req :params :name)
       address (-> req :remote-addr)]
       (ping name key address now)))
