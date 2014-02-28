{{#friends}}
<source>
    <name>piraspbay-{{ name }}</name>
    {{#ftp}}
    <path pathversion="1">sftp://{{ user }}:{{ pass }}@{{ ip }}:{{ port }}/./</path>
    {{/ftp}}
</source>
{{/friends}}
