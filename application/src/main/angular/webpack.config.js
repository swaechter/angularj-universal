var path = require('path');
var ngtools = require('@ngtools/webpack');
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: {
        server: './src/engine.ts'
    },
    resolve: {
        modules: [
            'node_modules',
            path.resolve(process.cwd(), 'src')
        ],
        extensions: ['.ts', '.js']
    },
    output: {
        path: path.join(process.cwd(), '../resources'),
        filename: '[name].bundle.js'
    },
    target: 'node',
    plugins: [
        new ngtools.AotPlugin({
            tsConfigPath: './tsconfig.json'
        }),
        new CopyWebpackPlugin([
            {
                from: 'src/index.html',
                to: '.'
            }
        ])
    ],
    module: {
        rules: [
            {
                test: /\.ts$/,
                loader: '@ngtools/webpack'
            },
            {
                test: /\.html/,
                use: 'raw-loader'
            }
        ]
    },
    devServer: {
        contentBase: './dist',
        port: 9000,
        inline: true,
        historyApiFallback: true,
        stats: 'errors-only',
        watchOptions: {
            aggregateTimeout: 300,
            poll: 500
        }
    }
};
