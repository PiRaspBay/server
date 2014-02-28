db.user.drop();
db.user.ensureIndex({
  name: 1
}, {
  unique: 1
});

db.user.insert({
  name: 'val',
  key: 'AEeD08o7',
  raspIp: '62.129.6.2',
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'tdu',
  key: 'f3xbJLG8E',
  raspIp: '62.129.6.2',
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'jba',
  key: 'f782673Nu',
  raspIp: '62.129.6.2',
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'the',
  key: 'f7888Jjrh',
  raspIp: '62.129.6.2',
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'renaud',
  key: '9Wjrb234h',
  raspIp: '62.129.6.2',
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'mamie',
  key: '26fh62ra',
  raspIp: '62.129.6.2',
  lastSeen: NumberLong("1393592794000")
});
db.user.insert({
  name: 'bob',
  key: '99arfytuh3',
  raspIp: '62.129.6.2',
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
  db.request.insert({from: x[0], to: x[1]});
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
  db.relation.insert({users: x});
});
