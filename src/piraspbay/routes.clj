(ns piraspbay.routes
    (:use [compojure.core :only [defroutes GET POST DELETE ANY context]]
          (ring.middleware [keyword-params :only [wrap-keyword-params]]
                           [params :only [wrap-params]]
                           [session :only [wrap-session]])
          [piraspbay.middleware :only [wrap-failsafe wrap-request-logging-in-dev
                                              wrap-reload-in-dev JGET JPUT JPOST JDELETE]])
    (:require [piraspbay.handlers.app :as app]
              [piraspbay.handlers.api :as api]
              [compojure.route :as route]))

;; define mapping here
(defroutes server-routes*
  (JPOST "/register" [] api/register)
  (context "/:me" []
    (GET "/configure" [] api/configure)
    (JGET "/friend" [] api/friend)
    (context "/request" []
      (JGET "/" [] api/request)
      (JPOST "/:user/accept" [] api/accept)
      (JDELETE "/:user" [] api/delete)
      (JPOST "/:user" [] api/new-request))
    (JGET "/:user" [] api/profile))
  ;; static files under ./public folder, prefix /static
  ;; like /static/css/style.css
  (route/files "/static")
  ;; 404, modify for a better 404 page
  (route/not-found "<p>Page not found.</p>" ))

(defn app [] (-> #'server-routes*
                 wrap-session
                 wrap-keyword-params
                 wrap-params
                 wrap-request-logging-in-dev
                 wrap-reload-in-dev
                 wrap-failsafe))
