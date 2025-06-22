import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AssetRequest } from 'src/app/models/assetRequest.model';
import { AssetRequestCreate } from 'src/app/models/assetRequestCreate.model';

@Injectable({
  providedIn: 'root'
})
export class AssetRequestService {

  baseUrl: string = "http://localhost:8080/api/users/asset-request/"
  constructor(private http: HttpClient) { }

  getAllData(): Observable<AssetRequest[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<AssetRequest[]>(this.baseUrl + "getall", { headers });
  }

  addData(assetRequest: AssetRequestCreate) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    console.log(assetRequest);
    return this.http.post<AssetRequest>(this.baseUrl + "add", assetRequest, { headers });
  }

  updateData(requestId: number, status:string) {
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

    return this.http.delete<string>(this.baseUrl + "delete/" + requestId, { headers, responseType: 'text' as 'json' });
  }

  getDataByUsersId(usersId:number)
  {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<AssetRequest[]>(this.baseUrl + "get/usersId/"+ usersId, { headers });

  }
}
