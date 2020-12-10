# Build
mvn clean package && docker build -t pl.wb/JwtJakartaEE .

# RUN

docker rm -f JwtJakartaEE || true && docker run -d -p 8080:8080 -p 4848:4848 --name JwtJakartaEE pl.wb/JwtJakartaEE 