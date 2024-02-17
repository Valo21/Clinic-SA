/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{html,ts}",
    ],
    theme: {
        extend: {
            boxShadow: {
                md: '0px 9px 30px rgba(7, 65, 210, 0.1)'
            }
        },
        keyframes: {
            'fade-in-down': {
                '0%': {
                    opacity: 0,
                    transform: 'translateY(-20px)'
                },
                '100%': {
                    opacity: 1
                }
            }
        },
        animation: {
            'fade-in-down': 'fade-in-down 0.6s ease-out',
        },
    },
    plugins: [],
}
