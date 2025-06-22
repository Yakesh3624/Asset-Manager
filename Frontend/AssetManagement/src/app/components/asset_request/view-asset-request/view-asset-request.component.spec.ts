import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAssetRequestComponent } from './view-asset-request.component';

describe('ViewAssetRequestComponent', () => {
  let component: ViewAssetRequestComponent;
  let fixture: ComponentFixture<ViewAssetRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAssetRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAssetRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
