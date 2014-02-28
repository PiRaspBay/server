<!DOCTYPE html>
<html>
  <!-- http://mustache.github.com/mustache.5.html -->
  <head>
    <!-- partial is just like copy and paste the template here -->
    {{>partials/header}}
  </head>
  <body>
    <nav class="navbar navbar-default" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">PiRaspBay</a>
        </div>
      </div>
    </nav>

    <div class="container">

      <h1>Truc de guedin</h1>
      <p>Use this document as a way to quick start any new project.<br> All you get is this message and a barebones HTML document.</p>

      <p>Your user agent is : {{ user-agent }}</p>

      <h3>Example list</h3>
      <ul>
        {{#list}}
          <li>{{ . }}</li>
        {{/list}}
      </ul>

    </div>
    <script src="/static/js/lib/jquery-2.1.0.min.js"></script>
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="/static/js/app.js"></script>
  </body>
</html>
