const path = require('path');
const webpack = require('webpack');

const HtmlWebPackPlugin = require('html-webpack-plugin');

const isProduction = (process.env.NODE_ENV === 'production');

const paths = {
  dist: path.resolve(__dirname, 'dist'),
  node: path.resolve(__dirname, 'node_modules'),
  src: path.resolve(__dirname, 'site')
};

const config = {
  // Base directory for our app (where app entry point is located)
  context: paths.src,

  // Dev server can be reached by running "npm start" and navigating to http://localhost:8080
  devServer: {
    compress: true,
    historyApiFallback: true,
    inline: true
  },

  // Generate source-maps on production only
  devtool: isProduction ? 'source-map' : 'eval',

  // Entry points for chunks (app and vendor) which ultimately results in compiled JS
  entry: {
    // Bundle for application specific code only (no vendor libraries)
    app: './index.js',
  },

  module: {
    rules: [
      // NOTE Babel loader expects .babelrc to know what transformations to run (latest, react, etc)
      {
        test: /\.js$/,
        include: paths.src,
        use: [{
          loader: 'babel-loader'
        }]
      },

      // NOTE ExtractTextPlugin will create a separate bundle for our CSS instead of bundling with JS
      {
        test: /\.s?css$/,
        use: [{
          loader: 'style-loader'
        }, {
          loader: 'css-loader'
        }, {
          loader: 'sass-loader'
        }]
      }
    ]
  },

  mode: isProduction ? 'production' : 'development',
  optimization: {
    splitChunks: {
      cacheGroups: {
        vendor: {
          chunks: "initial",
          test: path.resolve(__dirname, "node_modules"),
          name: "vendor",
          enforce: true
        }
      }
    }
  },

  output: {
    path: paths.dist,
    publicPath: '/',
    filename: '[name].bundle.js'
  },

  plugins: [

    // Will auto-generate dist/index.html with scripts injected
    new HtmlWebPackPlugin({
      template: `${paths.src}/index.html`,
      inject: 'body'
    }),

    // Filter out moment.js locales since we don't use them (significantly reduces file size by 100k final bundle)
    new webpack.ContextReplacementPlugin(/moment[\/\\]locale$/, /nb/),

    // Will allow us to access IS_PRODUCTION directly in source code
    new webpack.DefinePlugin({
      IS_PRODUCTION: process.env.NODE_ENV === 'production'
    }),

    // Will output module names when changes are logged to console (HMR)
    new webpack.NamedModulesPlugin(),
  ],

  /**
   * Allows us to resolve imports using the module path given various roots (node, src) instead of relative paths, e.g.:
   *      import Module from 'app/pages/module';
   *      import '~app/styles/colors.less';
   * Instead of...
   *      import Module from '../../src/app/pages/module';
   *      import '../../../app/styles/colors.less';
   */
  resolve: {
    extensions: ['.js', '.jsx', '.scss'],
    modules: [
      paths.node,
      paths.src
    ]
  }
};

module.exports = config;
