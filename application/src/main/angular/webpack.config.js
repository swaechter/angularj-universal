var path = require('path');
const ngtools = require('@ngtools/webpack');

module.exports = {
    entry: {
        main: './src/engine.ts'
    },
    resolve: {
        modules: [
            'node_modules',
            path.resolve(process.cwd(), 'src')
        ],
        extensions: ['.ts', '.js']
    },
    output: {
        path: path.join(process.cwd(), 'dist'),
        filename: '[name].js'
    },
    target: 'node',
    plugins: [
        new ngtools.AotPlugin({
            tsConfigPath: './tsconfig.json'
        })
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
