<sources>
    <programs>
        <default pathversion="1"></default>
    </programs>
    <video>
        <default pathversion="1"></default>
        {{#friends}}
        <source>
            <name>{{ name }}</name>
            {{#ftp}}
            <path pathversion="1">sftp://{{ user }}:{{ pass }}@{{ ip }}:{{ port }}/./</path>
            {{/ftp}}
        </source>
        {{/friends}}
    </video>
    <music>
        <default pathversion="1"></default>
    </music>
    <pictures>
        <default pathversion="1"></default>
    </pictures>
    <files>
        <default pathversion="1"></default>
    </files>
</sources>
