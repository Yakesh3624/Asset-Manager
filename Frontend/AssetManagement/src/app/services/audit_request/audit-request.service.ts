import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AssetAllocation } from 'src/app/models/assetAllocation.model';
import { AuditRequest } from 'src/app/models/auditRequest.model';
import { AuditRequestCreate } from 'src/app/models/auditRequestCreate.model';

@Injectable({
  providedIn: 'root'
})
export class AuditRequestService {

  baseUrl: string = "http://localhost:8080/api/users/audit-request/";
  constructor(private http: HttpClient) { }

  getAllData(): Observable<AuditRequest[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<AuditRequest[]>(this.baseUrl + "getall", { headers });
  }

  addData(assetAllocation: AssetAllocation) {
    console.log(assetAllocation);
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    let auditRequestCreate: AuditRequestCreate = {
      usersId: assetAllocation.usersId,
      assetNo: assetAllocation.assetNo
    };

    return this.http.post<AuditRequest>(this.baseUrl + "add", auditRequestCreate, { headers });
  }

  updateData(requestId:number,status:string) {
    const token = localStorage.getItem("token");
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<string>(`${this.baseUrl}update/status/${requestId}/${status}`,{}, { headers, responseType: 'text' as 'json' });
  }

  deleteData(requestId: number) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<any>(this.baseUrl + "delete/" + requestId, { headers, responseType: 'text' as 'json' });
  }
  getDataByUsersId(usersId:number): Observable<AuditRequest[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<AuditRequest[]>(this.baseUrl + "get/usersId/"+usersId, { headers });
  }
}
