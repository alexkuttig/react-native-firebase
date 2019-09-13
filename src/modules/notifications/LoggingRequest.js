/**
 * @flow
 * LoggingRequest representation wrapper
 */
import { NativeAndroidLoggingRequest } from './types';

export default class LoggingRequest {
  _url: string;

  _type: "GET" | "POST";

  _headers: ?{[headerName: string]: string};

  _body: ?Object;

  constructor(url: string, type: "GET" | "POST", headers: ?{[headerName: string]: string}, body: ?Object) {
    this._url = url;
    this._type = type;
    this._headers = headers;
    this._body = body;
  }

  get url(): string {
    return this._url;
  }

  get type(): "GET" | "POST" {
    return this._type;
  }

  get headers(): {[headerName: string]: string} {
    return this._headers;
  }

  get body(): ?Object {
    return this._body;
  }


  build(): NativeAndroidLoggingRequest {
    if (!this._url) {
      throw new Error('LoggingRequest: Missing required `url` property');
    } else if (!this._type) {
      throw new Error('LoggingRequest: Missing required `type` property');
    }

    return {
      url: this._url,
      type: this._type,
      headers: this._headers,
      body: this._body
    };
  }
}
