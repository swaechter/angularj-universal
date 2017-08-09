var path = require('path');

module.exports = {
    target: 'node',
    entry: {
        server: path.resolve(process.cwd(), 'server.js')
    },
    output: {
        path: path.resolve(process.cwd(), 'dist-serverbundle'),
        filename: '[name].bundle.js'
    },
    resolve: {
        modules: [
            'node_modules'
        ],
        extensions: [
            '.js'
        ]
    }
};
