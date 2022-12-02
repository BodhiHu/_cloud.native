FROM golang AS builder
WORKDIR /citta_go
COPY go.mod .
COPY go.sum .
RUN go mod download
COPY . .
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o /bin/citta_go .

FROM alpine:latest
RUN apk --no-cache add ca-certificates
RUN addgroup -S citta_go && adduser -S citta_go -G citta_go
USER citta_go
WORKDIR /home/citta_go
COPY --from=builder /bin/citta_go ./
EXPOSE 3000
ENTRYPOINT ["./citta_go"]
