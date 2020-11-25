# Getting Started
This repo contains a sample Redis cache implementation,
 aimed at learning how Cache is stored and fetched from Redis via Springboot.

### Start standalone Redis with Docker

```bash
docker run --name my-first-redis -p 6379:6379 -d redis
sudo apt install redis-tools
```
More info at https://phoenixnap.com/kb/docker-redis

### Redis keys check

```bash
sudo redis-cli -h 127.0.0.1 -p 6379
127.0.0.1:6379> KEYS *
GET "myCache::myCachePrefix_firstData"
TTL "myCache::myCachePrefix_firstData"
flushall
```

### References 
https://programmerfriend.com/ultimate-guide-to-redis-cache-with-spring-boot-2-and-spring-data-redis/