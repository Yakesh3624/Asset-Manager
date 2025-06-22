import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyauditComponent } from './myaudit.component';

describe('MyauditComponent', () => {
  let component: MyauditComponent;
  let fixture: ComponentFixture<MyauditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyauditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyauditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
