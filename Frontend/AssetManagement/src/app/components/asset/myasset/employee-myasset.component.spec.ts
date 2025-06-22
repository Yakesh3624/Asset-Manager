import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeMyassetComponent } from './employee-myasset.component';

describe('EmployeeMyassetComponent', () => {
  let component: EmployeeMyassetComponent;
  let fixture: ComponentFixture<EmployeeMyassetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmployeeMyassetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmployeeMyassetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
