(ns piraspbay.app-test
  (:use piraspbay.test-common
        clojure.test))

(deftest test-landing-page
  (let [resp (test-app {:request-method :get
                        :uri "/"})]
    (is (= 200 (:status resp)))))

(deftest test-get-time
  (let [resp (test-app {:request-method :get
                        :uri "/api/time"})]
    (is (= 200 (:status resp)))
    (is (-> resp :body read-json :time))))
