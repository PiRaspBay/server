db.user.drop();
db.user.ensureIndex({
  name: 1
}, {
  unique: 1
});

db.user.insert({
  name: 'val',
  key: 'AEeD08o7',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-val',
    pass: 'sftp-pass',
    port: 22
  },
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'tdu',
  key: 'f3xbJLG8E',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-tdu',
    pass: 'sftp-pass',
    port: 19022
  },
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'jba',
  key: 'f782673Nu',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-jba',
    pass: 'sftp-pass',
    port: 19022
  },
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'the',
  key: 'f7888Jjrh',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-the',
    pass: 'sftp-pass',
    port: 22
  },
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'renaud',
  key: '9Wjrb234h',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-re',
    pass: 'sftp-pass',
    port: 22
  },
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'mamie',
  key: '26fh62ra',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-mamie',
    pass: 'sftp-pass',
    port: 23
  },
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'bob',
  key: '99arfytuh3',
  ftp: {
    ip: '62.129.6.2',
    user: 'sftp-bob',
    pass: 'sftp-pass',
    port: 24
  },
  lastSeen: NumberLong("1393592794000")
});

db.request.drop();
db.request.ensureIndex({
  from: 1,
  to: 1
}, {
  unique: 1
});

[
  ['tdu', 'val'],
  ['jba', 'val'],
  ['jba', 'tdu'],
  ['the', 'val'],
  ['the', 'tdu'],
  ['tdu', 'the'],
  ['mamie', 'tdu'],
  ['bob', 'jba']
].forEach(function(x) {
  db.request.insert({
    from: x[0],
    to: x[1]
  });
});

db.relation.drop();
db.relation.ensureIndex({
  users: 1
});

[
  ['tdu', 'renaud'],
  ['jba', 'mamie'],
  ['jba', 'renaud'],
  ['mamie', 'renaud'],
  ['mamie', 'val'],
  ['mamie', 'the'],
  ['mamie', 'bob'],
  ['mamie', 'renaud'],
  ['bob', 'tdu'],
  ['bob', 'val'],
  ['bob', 'the']
].forEach(function(x) {
  db.relation.insert({
    users: x
  });
});
