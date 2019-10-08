consul {
  address = "discovery:8500"

  retry {
    enabled = true
    attempts = 100
    backoff = "250ms"
  }
}

template {
  source = "/etc/nginx/conf.d/app-config.ctmpl"
  destination = "/etc/nginx/conf.d/app.conf"
  perms = 0600
  command = "service nginx reload"
}