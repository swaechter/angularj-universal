require('zone.js/dist/zone-node');

const socketEngine = require('@nguniversal/socket-engine');
const {AppServerModuleNgFactory} = require('./dist/angular-server/main');

const port: Number = parseInt(process.env.NODEPORT) || 9090;

console.log('Going to start the server on port: ' + port);
socketEngine.startSocketEngine(AppServerModuleNgFactory, [], 'localhost', port);
