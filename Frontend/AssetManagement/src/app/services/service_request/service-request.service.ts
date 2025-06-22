import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceRequest } from 'src/app/models/serviceRequest.model';
import { ServiceRequestCreate } from 'src/app/models/serviceRequestCreate.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceRequestService {

  baseUrl: string = "http://localhost:8080/api/users/service-request/"
  constructor(private http: HttpClient) { }

  getAllData(): Observable<ServiceRequest[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<ServiceRequest[]>(this.baseUrl + "getall", { headers });
  }

  addData(serviceRequest: ServiceRequestCreate) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<ServiceRequest>(this.baseUrl + "add", serviceRequest, { headers, responseType:'text' as 'json' });
  }

  updateData(requestId: number, status: string) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<string>(`${this.baseUrl}update/status/${requestId}/${status}`, {}, { headers, responseType: 'text' as 'json' });
  }

  deleteData(requestId: number) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<any>(this.baseUrl + "delete/" + requestId, { headers, responseType: 'text' as 'json' });
  }

  getDataByUsersId(usersId:number): Observable<ServiceRequest[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<ServiceRequest[]>(this.baseUrl + "get/usersId/"+usersId, { headers });
  }
}
