require('zone.js/dist/zone-node');

const socketEngine = require('@nguniversal/socket-engine');
const {AppServerModuleNgFactory} = require('./dist/angular-server/main');

console.log('Going to start the server!');
socketEngine.startSocketEngine(AppServerModuleNgFactory);
