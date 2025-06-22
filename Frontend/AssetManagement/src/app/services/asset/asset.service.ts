import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Asset } from 'src/app/models/asset.model';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  baseUrl: string = "http://localhost:8080/api/users/asset/"
  constructor(private http: HttpClient) { }

  getAllData(): Observable<Asset[]> {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Asset[]>(this.baseUrl + "getall", { headers });
  }

  addData(asset: Asset) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    console.log(asset);
    return this.http.post<Asset>(this.baseUrl + "add", asset, { headers });
  }

  updateData(asset:Asset)
  {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<string>(this.baseUrl+"update",asset,{headers, responseType:'text' as 'json'});
  }

  deleteData(assetNo: number) {
    const token = localStorage.getItem("token");

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<any>(this.baseUrl+"delete/"+assetNo,{headers,responseType:'text' as 'json'});
  }
}
