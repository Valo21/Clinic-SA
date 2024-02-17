import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SkeletonScreenComponent } from './skeleton-screen.component';

describe('SkeletonScreenComponent', () => {
  let component: SkeletonScreenComponent;
  let fixture: ComponentFixture<SkeletonScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SkeletonScreenComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SkeletonScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
