import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Users } from 'src/app/models/users.model';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  baseUrl: string = "http://localhost:8080/api/authenticate/"

  constructor(private http: HttpClient) { }

  addData(users: Users) {

    return this.http.post<Users>(this.baseUrl + "login/register", users);
  }
}
