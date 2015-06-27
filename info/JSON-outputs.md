#Sample JSON-Outputs from telegram:
##message
{"event": "message", "service": false, "flags": 257, "text": "hi", "id": 4666, "from": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "to": {"username": "RPi_username", "flags": 267, "id": 87654321, "first_name": "Raspberry", "phone": "4912345678901", "print_name": "Raspberry_Pi", "last_name": "Pi", "type": "user"}, "date": 1435076540, "out": false, "unread": true}

##online-status
{"event": "online-status", "user": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "online": true, "state": 1, "when": "2015-06-23 18:27:15"}

##forwarded message
{"event": "message", "fwd_date": 1435076540, "fwd_from": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "service": false, "flags": 259, "text": "hi", "id": 4667, "from": {"username": "RPi_username", "flags": 267, "id": 87654321, "first_name": "Raspberry", "phone": "4912345678901", "print_name": "Raspberry_Pi", "last_name": "Pi", "type": "user"}, "to": {"title": "RaPi-Nachrichtenlog", "flags": 256, "id": 16617964, "print_name": "RaPi-Nachrichtenlog", "members_num": 2, "type": "chat", "admin": {"id": 0, "print_name": "user#0", "type": "user"}}, "date": 1435076540, "out": true, "unread": true}

##outgoing message
{"event": "message", "service": false, "flags": 16643, "text": "Ich verstehe dich nicht 😨 Was soll ich tun?? 😰", "id": 4668, "from": {"username": "RPi_username", "flags": 267, "id": 87654321, "first_name": "Raspberry", "phone": "4912345678901", "print_name": "Raspberry_Pi", "last_name": "Pi", "type": "user"}, "to": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "date": 1435076540, "out": true, "unread": true}

##read on outgoing message
{"event": "read", "service": false, "flags": 16642, "text": "Ich verstehe dich nicht 😨 Was soll ich tun?? 😰", "id": 4668, "from": {"username": "RPi_username", "flags": 267, "id": 87654321, "first_name": "Raspberry", "phone": "4912345678901", "print_name": "Raspberry_Pi", "last_name": "Pi", "type": "user"}, "to": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "date": 1435076540, "out": true, "unread": false}

##offline-status
{"event": "online-status", "user": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "online": false, "state": -1, "when": "2015-06-23 18:22:26"}

##offline-status self
{"event": "online-status", "user": {"username": "RPi_username", "flags": 267, "id": 87654321, "first_name": "Raspberry", "phone": "4912345678901", "print_name": "Raspberry_Pi", "last_name": "Pi", "type": "user"}, "online": false, "state": -1, "when": "2015-06-23 18:22:37"}

##new message in group
{"event": "message", "service": false, "flags": 257, "text": "hey", "id": 4741, "from": {"username": "my_username", "flags": 259, "id": 12345678, "first_name": "Ich", "phone": "4915781234567", "print_name": "Ich", "last_name": "", "type": "user"}, "to": {"title": "Chat title", "flags": 256, "id": 567891234, "print_name": "Chat print name", "members_num": 2, "type": "chat", "admin": {"id": 0, "print_name": "user#0", "type": "user"}}, "date": 1435442761, "out": false, "unread": true}

###beautified:
{
  "event": "message",
  "service": false,
  "flags": 257,
  "text": "hey",
  "id": 4741,
  "from": {
    "username": "my_username",
    "flags": 259,
    "id": 12345678,
    "first_name": "Ich",
    "phone": "4915781234567",
    "print_name": "Ich",
    "last_name": "",
    "type": "user"
  },
  "to": {
    "title": "Chat title",
    "flags": 256,
    "id": 567891234,
    "print_name": "Chat print name",
    "members_num": 2,
    "type": "chat",
    "admin": {
      "id": 0,
      "print_name": "user#0",
      "type": "user"
    }
  },
  "date": 1435442761,
  "out": false,
  "unread": true
}