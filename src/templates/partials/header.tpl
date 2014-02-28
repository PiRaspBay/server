<!-- if has title, use the title -->
{{#title}}<title>{{title}}</title>{{/title}}

  <!-- No title, use default one -->
{{^title}}<title>{{sanitized-ns}}</title>{{/title}}
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="/static/css/style.css" rel="stylesheet"/>
<link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
{{#dev?}}
  <!-- dev specific code -->
{{/dev?}}
{{#prod?}}
  <!-- production specific code -->
{{/prod?}}
