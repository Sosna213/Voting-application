
module.exports = [
  {
    context:["/register", "/login"],
    target: "http://localhost:8080",
    secure: false,
    logLevel: "debug"
  }
]
