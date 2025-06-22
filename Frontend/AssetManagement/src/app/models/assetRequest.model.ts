import { timestamp } from "rxjs";

export interface AssetRequest
{
  requestId:number;
	requestedAt:Date;
	status:string;
	assetNo:number;
	usersId:number;
}
