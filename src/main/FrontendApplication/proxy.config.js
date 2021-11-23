
module.exports = [
  {
    context:["/register", "/login", "/users", "/token-refresh"],
    target: "http://localhost:8080",
    secure: false,
    logLevel: "debug"
  }
]
