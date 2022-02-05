package activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import com.talha.application.openotheractivity.*
import entity.DeviceInfo

class SecondActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextNumber: EditText
    private lateinit var mSwitchOpenStatus: Switch
    private lateinit var mDeviceInfo: DeviceInfo

    private fun setDeviceInfoViews() {
        mEditTextName.setText(mDeviceInfo.name)
        mEditTextNumber.setText(mDeviceInfo.number.toString())
        mSwitchOpenStatus.isChecked = mDeviceInfo.isOpen
    }

    private fun setDeviceInfo() {
        var name = mEditTextName.text.toString()
        val number = mEditTextNumber.text.toString().toLong()
        val isOpen = mSwitchOpenStatus.isChecked
        mDeviceInfo = DeviceInfo(name, number, isOpen)
    }

    private fun initViews() {
        mEditTextName = findViewById(R.id.secondActivityEditTextName)
        mEditTextNumber = findViewById(R.id.secondActivityEditTextNumber)
        mSwitchOpenStatus = findViewById(R.id.secondActivitSwitchOpenStatus)
    }

    private fun initialize() {
        initViews()
        mDeviceInfo = intent.getSerializableExtra(DEVICE_INFO) as DeviceInfo
        setDeviceInfoViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        initialize()
    }

    fun onOpenThirdActivityClicked(view: View) {
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }

    fun onExitButtonClicked(view: View) {
        finish()
    }

    fun onOKButtonClicked(view: View) {
        Intent().apply {
            setDeviceInfo()
            this.putExtra(DEVICE_INFO, mDeviceInfo)
            setResult(RESULT_OK, this)
            finish()
        }
    }
}