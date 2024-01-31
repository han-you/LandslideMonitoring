import Vue from "vue";
import Vuetify from "vuetify";
import '@mdi/font/css/materialdesignicons.css'
Vue.use(Vuetify)
export default new Vuetify({
    icons: {
        defaultSet: 'mdi',
    },
    theme: {
        themes: {
            light: {
                primary: '#3e5fe2',
                secondary: '#aeb8fe',
                accent: '#27187e',
                error: '#e71d36',
                info: '#bbd0ff',
                success: '#a7c957',
                warning: '#eb5e28',

                dark: "#050A19",
                light: "#f5f8f9",
                "strong-text": "#ddeeff",
                "footer-bg": "#121930",
                text: "#a8bdd2",
                "text-light": "#646984",
            },
            dark: {
                primary: '#283618',
                secondary: '#606c38',
                accent: '#fefae0',
                error: '#e71d36',
                info: '#bbd0ff',
                success: '#a7c957',
                warning: '#eb5e28',

                dark: "#050A19",
                light: "#f5f8f9",
                "strong-text": "#ddeeff",
                "footer-bg": "#121930",
                text: "#a8bdd2",
                "text-light": "#646984",
            },
        },
    },
});
