
export interface Asset
{
  assetNo:number;
	assetName:string;
	category:string;
	assetModel:string;
	manufacturingDate:Date;
	expiryDate:Date;
	assetValue:number;
	availability:string;
  image: File | null |any;
}
