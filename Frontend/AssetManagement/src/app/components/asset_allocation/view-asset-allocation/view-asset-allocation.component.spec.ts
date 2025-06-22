import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAssetAllocationComponent } from './view-asset-allocation.component';

describe('ViewAssetAllocationComponent', () => {
  let component: ViewAssetAllocationComponent;
  let fixture: ComponentFixture<ViewAssetAllocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAssetAllocationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAssetAllocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
