# @void/sodium-universal

Low level bindings for [libsodium](https://github.com/jedisct1/libsodium) that
work across all platforms.

This is a fork of [sodium-native](https://github.com/sodium-friends/sodium-native)
adding support for web, NativeScript, and React Native.

While there is [sodium-universal](https://github.com/sodium-friends/sodium-universal)
that aims to support additional platforms, it uses [sodium-javascript](https://github.com/sodium-friends/sodium-javascript) for non-node environments.

Pure javascript implementation of libsodium is hardly practical and existing implementation
is woefully incomplete.

`@void/sodium-universal` uses API-compatible wasm build on the web/NativeScript,
and custom JSI module on React Native, running the original libsodium code across
all platforms.

```
npm install @void/sodium-universal
```

The goal of this project is to be thin, stable, unopionated wrapper around libsodium.

All methods exposed are more or less a direct translation of the libsodium c-api.
This means that most data types are buffers and you have to manage allocating return values and passing them in as arguments intead of receiving them as return values.

This makes this API harder to use than other libsodium wrappers out there, but also means that you'll be able to get a lot of perf / memory improvements as you can do stuff like inline encryption / decryption, re-use buffers etc.

This also makes this library useful as a foundation for more high level crypto abstractions that you want to make.

## Usage

``` js
var sodium = require('@void/sodium-universal')

var nonce = Buffer.alloc(sodium.crypto_secretbox_NONCEBYTES)
var key = sodium.sodium_malloc(sodium.crypto_secretbox_KEYBYTES) // secure buffer
var message = Buffer.from('Hello, World!')
var ciphertext = Buffer.alloc(message.length + sodium.crypto_secretbox_MACBYTES)

sodium.randombytes_buf(nonce) // insert random data into nonce
sodium.randombytes_buf(key)  // insert random data into key

// encrypted message is stored in ciphertext.
sodium.crypto_secretbox_easy(ciphertext, message, nonce, key)

console.log('Encrypted message:', ciphertext)

var plainText = Buffer.alloc(ciphertext.length - sodium.crypto_secretbox_MACBYTES)

if (!sodium.crypto_secretbox_open_easy(plainText, ciphertext, nonce, key)) {
  console.log('Decryption failed!')
} else {
  console.log('Decrypted message:', plainText, '(' + plainText.toString() + ')')
}
```

## Documentation

Complete documentation may be found on the [sodium-friends website](https://sodium-friends.github.io/docs/docs/getstarted)

## License

MIT
