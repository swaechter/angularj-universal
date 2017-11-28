const path = require('path');

module.exports = {
    target: 'node',
    entry: {
        server: path.join(__dirname, './library/server.ts')
    },
    output: {
        path: path.resolve(__dirname, './../resources'),
        filename: '[name].bundle.js'
    },
    resolve: {
        modules: [
            'node_modules'
        ],
        extensions: [
            '.ts',
            '.js'
        ]
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                loader: 'ts-loader'
            }
        ]
    }
};
