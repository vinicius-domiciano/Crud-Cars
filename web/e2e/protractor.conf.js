
/**
 * @type { import("protractor").Config }
 */
exports.config = {
    seleniumAddress: 'http://localhost:4444/wd/hub',

    specs: ['./src/features/*.feature'],

    baseURL: 'http://localhost:4200/',

    framework: 'custom',
    capabilities: {
        'browserName': 'chrome',
        'platform': 'windows'
    },
    directConnect: true,

    frameworkPath: require.resolve('protractor-cucumber-framework'),
    cucumberOpts: {
        require: ['./src/steps/*.ts'],
    },

    SELENIUM_PROMISE_MANAGER: true,
    onPrepare() {
        require('ts-node').register({
            project: require('path').join(__dirname, './tsconfig.json')
        });
    }

};