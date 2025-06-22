import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Credential } from 'src/app/models/credential.model';

@Injectable({
  providedIn: 'root'
})
export class CredentialService {
    baseUrl: string = "http://localhost:8080/api/authenticate/"

  constructor(private http: HttpClient) { }

  addData(credential: Credential) {

    return this.http.post<Credential>(this.baseUrl + "set/username-password", credential);
  }
}
