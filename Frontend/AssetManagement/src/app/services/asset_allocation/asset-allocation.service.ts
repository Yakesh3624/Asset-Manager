import { Injectable } from '@angular/core';
import { AssetAllocation } from '../../models/assetAllocation.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AssetAllocationService {

  baseUrl: string = "http://localhost:8080/api/users/asset-allocation/"
  constructor(private http: HttpClient) { }

  getAllData(): Observable<AssetAllocation[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<AssetAllocation[]>(this.baseUrl + "getall", { headers });
  }

  getDataByUsersId(usersId:number)
  {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<AssetAllocation[]>(this.baseUrl + "get/users-Id/"+usersId, { headers });
  }

  // addData(assetAallocation: AssetAllocation) {
  //   const token = localStorage.getItem("token");

  //   const headers = new HttpHeaders({
  //     'Authorization': `Bearer ${token}`
  //   });
  //   return this.http.post<AssetAllocation>(this.baseUrl + "add", assetAallocation, { headers });
  // }

  // updateData(assetAallocation:AssetAllocation)
  // {
  //   const token = localStorage.getItem("token");

  //   const headers = new HttpHeaders({
  //     'Authorization': `Bearer ${token}`
  //   });
  //   return this.http.put<string>(this.baseUrl+"update",assetAallocation,{headers, responseType:'text' as 'json'});
  // }

  deleteData(allocationId: number) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<string>(this.baseUrl+"return/"+allocationId,{headers,responseType:'text' as 'json'});
  }
}
