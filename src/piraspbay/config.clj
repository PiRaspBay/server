(ns piraspbay.config)

(defonce app-configs (atom {:profile :dev
                            :mongodb {:host "127.0.0.1"
                                      :port 27017
                                      :db "piraspbay"
                                      :rasp-coll "rasp"}}))

(defn cfg [key & [default]]
  (if-let [v (or (key @app-configs) default)]
    v
    (when-not (contains? @app-configs key)
      (throw (RuntimeException. (str "unknow config for key " (name key)))))))
