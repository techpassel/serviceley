import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
//To use 'crypto.js you need to install followings pakages(both necessary):
//npm i @types/crypto-js --save-dev
//npm i crypto-js --save
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export default class SessionUtil {
    sessionSecret = environment.sessionSecret;
    saveSession(sessionData: any) {
        const session = this.encryptAES(sessionData);
        localStorage.setItem("session", session);
    }

    getSession() {
        const session = localStorage.getItem("session");
        if (session != null)
            return this.decryptAES(session);
    }

    sessionExist() {
        const session = localStorage.getItem("session");
        if (session != null) {
            let sessionData = this.decryptAES(session);
            if ('token' in sessionData) {
                return true
            }
        }
        return false;
    }

    clearSession() {
        const session = localStorage.getItem("session");
        if (session != null) {
            let sessionData = this.decryptAES(session);
            if ('token' in sessionData) {
                localStorage.removeItem('session');
                return sessionData['token']
            }
        }
    }

    encryptAES = (data: any) => {
        return CryptoJS.AES.encrypt(JSON.stringify(data), this.sessionSecret).toString();
    };

    decryptAES = (encryptedBase64: string) => {
        try {
            const decrypted = CryptoJS.AES.decrypt(encryptedBase64, this.sessionSecret);
            if (decrypted) {
                return JSON.parse(decrypted.toString(CryptoJS.enc.Utf8));
            }
        } catch (err) {
            throw new Error("Error in decrypting data");
        }
    };
}