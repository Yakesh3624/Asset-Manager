import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Credential } from 'src/app/models/credential.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl: string = "http://localhost:8080/api/authenticate/"

  constructor(private http: HttpClient, private router: Router) { }


  login(credential: Credential): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(this.baseUrl + "login", credential);
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    const payload = JSON.parse(atob(token.split('.')[1]));
    console.log("role :"+token);
    return payload.role;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUsersId(userName:string):Observable<Credential>
  {
    return this.http.get<Credential>("http://localhost:8080/api/credential/get-by-username/"+userName);
  }
}
