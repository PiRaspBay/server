(ns piraspbay.rasp
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))

(defn init-db! [config]

  (mg/connect! {:host (:host config)
                :port (:port config)})
  (mg/set-db! (mg/get-db (:db config)))

  (def rasp-coll (:rasp-coll config))

  (mc/insert-and-return rasp-coll {:name "Le rasp à Renaud"
                                   :key "secretkey"
                                   :last-seen 0}))
