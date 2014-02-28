(ns piraspbay.rasp
  (:use [piraspbay.config :only [cfg]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))

(def config (cfg :mongodb))
(def coll (:coll config))

(defn ping [name key ip date]
  (println (str "Pinged " name " " key " " ip " " date)))
  ; (mc/insert-and-return coll {:name "Le rasp à Renaud"
  ;                                  :key "secretkey"
  ;                                  :last-seen 0}))

; (def get-by-key [key]
  ; (mc/find-one coll {:ip key})
