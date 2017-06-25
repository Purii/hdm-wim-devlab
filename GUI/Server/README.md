# WebSocket Demo

The WebSocket API allows you to to maintain a continuous two-way connection between client and server.
Messages can be sent and received on both ends.

Article URL: http://www.sitepoint.com/real-time-apps-websockets-server-sent-events

## Requirements

* [Node.js](http://nodejs.org/)

## Installation Steps

```bash
npm install
npm start
open http://localhost:8080/
```

Try opening multiple browsers and looking at the logs on client and server.
Here's what's happening:

- The client connects to `ws://localhost:8081/` via a `WebSocket`
- The client sends `{ message: 'Hello' }` to the server
- The server sends `{ message: 'Gotcha '}` when a connection is established.
- The server keeps track of all connections in `wss.clients`
- Every three seconds the server broadcasts `{ message: 'Hello hello!' }` to all connections.
- Connections can be closed by hitting the `Close` button or closing the window.
- You can send `{ message: 'Hey' }` to the server by hitting `Send Message`.
- The server sends `{ message: 'Something changed' }` to all connections when a message is received.

## Links

- [React to data changes](http://rauchg.com/2014/7-principles-of-rich-web-applications/#react-to-data-changes)
- [Writing WebSocket client applications](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications)
- [Writing WebSocket servers](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers)
- [WebSocket](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket)

## License

The MIT License (MIT)

Copyright (c) 2016 SitePoint

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

