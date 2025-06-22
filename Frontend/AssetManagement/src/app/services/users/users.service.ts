import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Users } from 'src/app/models/users.model';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  baseUrl: string = "http://localhost:8080/api/users/"
  constructor(private http: HttpClient) { }

  getAllData(): Observable<Users[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Users[]>(this.baseUrl + "getall", { headers });
  }

  getDataByUsersId(usersId:number): Observable<Users> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Users>(this.baseUrl + "get/"+usersId, { headers });
  }

  // addData(users:Users) {
  //   const token = localStorage.getItem("token");

  //   const headers = new HttpHeaders({
  //     'Authorization': `Bearer ${token}`
  //   });

  //   return this.http.post<Users>(this.baseUrl + "add", users, { headers });
  // }

  updateData(users:Users) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<string>(this.baseUrl + "update", users, { headers, responseType: 'text' as 'json' });
  }

  deleteData(usersId: number) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<any>(this.baseUrl + "delete/" + usersId, { headers, responseType: 'text' as 'json' });
  }
}
